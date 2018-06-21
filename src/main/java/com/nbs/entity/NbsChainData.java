
package com.nbs.entity;

import java.io.Serializable;
import java.util.List;

public class NbsChainData implements Serializable {
    private String Hash;
    private Long Size;
    private String Name;
    private String Type;
    private List<NbsChainData> Links;

    public NbsChainData(String hash, Long size, String name, String type, List<NbsChainData> links) {
        Hash = hash;
        Size = size;
        Name = name;
        Type = type;
        Links = links;
    }

    public NbsChainData(String hash, Long size, String name, String type) {
        Hash = hash;
        Size = size;
        Name = name;
        Type = type;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }

    public Long getSize() {
        return Size;
    }

    public void setSize(Long size) {
        Size = size;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<NbsChainData> getLinks() {
        return Links;
    }

    public void setLinks(List<NbsChainData> links) {
        Links = links;
    }

    enum NbsChainType{
        Directory,File
    }
}
