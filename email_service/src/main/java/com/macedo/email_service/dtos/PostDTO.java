package com.macedo.email_service.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

public record PostDTO(int id, String title, AuthorDTO author, String content, Timestamp publishedAt) implements Serializable {
}
