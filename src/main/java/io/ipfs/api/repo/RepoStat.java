package io.ipfs.api.repo;

/**
 * @Package : io.ipfs.api.repo
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-1:49
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class RepoStat {
    private Integer NumObjects;
    private Long RepoSize;
    private String RepoPath;
    private String Version;
    private Long StorageMax;

    public Integer getNumObjects() {
        return NumObjects;
    }

    public void setNumObjects(Integer numObjects) {
        NumObjects = numObjects;
    }

    public Long getRepoSize() {
        return RepoSize;
    }

    public void setRepoSize(Long repoSize) {
        RepoSize = repoSize;
    }

    public String getRepoPath() {
        return RepoPath;
    }

    public void setRepoPath(String repoPath) {
        RepoPath = repoPath;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public Long getStorageMax() {
        return StorageMax;
    }

    public void setStorageMax(Long storageMax) {
        StorageMax = storageMax;
    }
}
