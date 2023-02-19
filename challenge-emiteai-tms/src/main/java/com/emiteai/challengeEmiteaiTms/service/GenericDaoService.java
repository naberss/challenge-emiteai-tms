package com.emiteai.challengeEmiteaiTms.service;

import com.emiteai.challengeEmiteaiTms.data.domain.Product;
import com.emiteai.challengeEmiteaiTms.exception.ElementNotFoundException;

import java.util.List;

public interface GenericDaoService {

    public Object findById(Long id) throws ElementNotFoundException;
}
