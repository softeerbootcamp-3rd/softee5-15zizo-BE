package com.zizo.carteeng.repository;

import com.zizo.carteeng.domain.MemoryMember;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository {

    private static ConcurrentHashMap<String, MemoryMember> store = new ConcurrentHashMap<>();

    public MemoryMember save(String id, MemoryMember member) {
        return store.put(id, member);
    }

    public Optional<MemoryMember> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

}
