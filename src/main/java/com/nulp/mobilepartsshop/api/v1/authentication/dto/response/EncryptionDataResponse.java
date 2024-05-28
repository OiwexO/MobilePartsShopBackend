package com.nulp.mobilepartsshop.api.v1.authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record EncryptionDataResponse(String cipher, String keyAlgorithm, String publicKeyBase64) {

}
