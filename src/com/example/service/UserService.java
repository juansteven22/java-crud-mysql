package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Filtrar usuarios por nombre
    public List<User> filterUsersByName(String namePart) {
        return userDAO.getAll().stream()
                .filter(user -> user.getName().toLowerCase().contains(namePart.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Contar usuarios por dominio de email
    public Map<String, Long> countUsersByEmailDomain() {
        return userDAO.getAll().stream()
                .collect(Collectors.groupingBy(
                        user -> user.getEmail().substring(user.getEmail().indexOf("@") + 1),
                        Collectors.counting()
                ));
    }

    // Obtener el promedio de longitud de nombres de usuario
    public double getAverageNameLength() {
        return userDAO.getAll().stream()
                .mapToInt(user -> user.getName().length())
                .average()
                .orElse(0);
    }

    // Obtener usuarios con IDs en un rango específico
    public List<User> getUsersInIdRange(int minId, int maxId) {
        return userDAO.getAll().stream()
                .filter(user -> user.getId() >= minId && user.getId() <= maxId)
                .collect(Collectors.toList());
    }

    // Obtener el usuario con el nombre más largo
    public User getUserWithLongestName() {
        return userDAO.getAll().stream()
                .max((u1, u2) -> Integer.compare(u1.getName().length(), u2.getName().length()))
                .orElse(null);
    }
}