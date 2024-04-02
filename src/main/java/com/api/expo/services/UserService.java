package com.api.expo.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

import com.api.expo.config.JwtService;
import com.api.expo.models.AuthResponse;
import com.api.expo.models.PasswordResetToken;
import com.api.expo.models.Role;
import com.api.expo.models.User;
import com.api.expo.models.Validation;
import com.api.expo.repository.PasswordResetTokenRepository;
import com.api.expo.repository.RoleRepository;
import com.api.expo.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ValidationService validationService;
    private MailSenderService mailSenderService;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    private EntityManager entityManager;

    @Transactional // Assure que la transaction est gérée correctement
    public User register(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Votre e-mail n'est pas valide!");
        }

        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(user.getPassword());

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");
        }

        Optional<User> existingUserOptional = userRepository.findByEmail(user.getEmail());
        existingUserOptional.ifPresent(existingUser -> {
            throw new RuntimeException("Un utilisateur avec cet e-mail existe déjà.");
        });

        Long roleId = user.getRoleId(); // Récupérer l'ID du rôle depuis l'utilisateur
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Le rôle spécifié n'existe pas."));

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(role); // Associer le rôle à l'utilisateur

        user = entityManager.merge(user);

        validationService.save(user);

        return user;
    }

    public User activation(Map<String, String> activation) {
        Validation validation = this.validationService.readCode(activation.get("code"));

        if (validation == null) {
            throw new RuntimeException("La validation n'a pas été trouvée pour le code fourni.");
        }

        Optional<User> userOptional = this.userRepository.findById(validation.getUser().getId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(true);
            user.setEmailVerifyAt(Instant.now());
            validation.setActivation(Instant.now());
            User updatedUser = this.userRepository.save(user); // Utilisez save() pour mettre à jour l'utilisateur

            return updatedUser;
        } else {
            throw new RuntimeException("L'utilisateur correspondant à la validation n'a pas été trouvé.");
        }
    }

    public AuthResponse connection(User user) {
        Optional<User> userExist = this.userRepository.findByEmail(user.getEmail());

        if (!userExist.isPresent()) {
            throw new RuntimeException("Utilisateur non trouvé!");
        }

        User existingUser = userExist.get();

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect!");
        }

        if (!existingUser.getEnabled()) { // Pas besoin de comparer avec true
            throw new RuntimeException("Votre compte n'est pas actif!");
        }
        String token = jwtService.generateToken(existingUser);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(existingUser);
        authResponse.setToken(token);

        return authResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByFirstname(username);
    }

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository; // Repository pour gérer les tokens de
                                                                       // réinitialisation de mot de passe

    @Transactional
    public User forgotPassword(User user) {
        // Générez un token de réinitialisation de mot de passe unique
        String resetToken = generateResetToken();

        // Stockez le token de réinitialisation et sa date d'expiration dans la base de
        // données
        storeResetToken(user, resetToken);

        // Envoyez un e-mail à l'utilisateur avec le lien de réinitialisation contenant
        // le token
        this.mailSenderService.sendResetEmail(user, resetToken);
        return user;
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    private void storeResetToken(User user, String resetToken) {
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(1); // Expiration dans 1 heure
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setExpiredDate(expirationTime);
        passwordResetTokenRepository.save(passwordResetToken);
    }

}
