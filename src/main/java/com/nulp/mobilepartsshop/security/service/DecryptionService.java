package com.nulp.mobilepartsshop.security.service;

import com.nulp.mobilepartsshop.core.entity.security.EncryptionData;
import com.nulp.mobilepartsshop.exception.security.DecryptionException;

public interface DecryptionService {

    EncryptionData getEncryptionData();

    String decrypt(String encrypted) throws DecryptionException;
}
