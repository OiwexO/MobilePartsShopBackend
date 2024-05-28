package com.nulp.mobilepartsshop.api.v1.authentication.dto.response;

import lombok.Builder;

@Builder
public record EncryptionDataResponse(String cipher, String keyAlgorithm, String publicKeyBase64) {

}
