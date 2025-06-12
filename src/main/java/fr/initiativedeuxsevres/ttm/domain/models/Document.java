package fr.initiativedeuxsevres.ttm.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;

    private byte[] fichierPdf;

    @Column(name = "user_id")
    private String userId;
}