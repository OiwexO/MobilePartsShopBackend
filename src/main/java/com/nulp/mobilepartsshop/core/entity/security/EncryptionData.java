package com.nulp.mobilepartsshop.core.entity.security;

import lombok.Builder;

@Builder
public record EncryptionData(String cipher, String keyAlgorithm, String publicKeyBase64) {
}
