package com.macedo.email_service.dtos;

import java.io.Serializable;

public record AuthorDTO(int id, String name, String email) implements Serializable {
}
