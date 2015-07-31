package org.troy.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

@Entity
@Table(name = "platform_item")
public class PlatformItem extends IdEntity {
    private static final long serialVersionUID = -6168800446652612835L;

    @Column(length = 200)
    private String            name;

    @Column(length = 20)
    private String            itemEarn;

    @Column(length = 400)
    private String            itemDesc;

    @Column(length = 11)
    private Long              itemParentId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemEarn() {
        return itemEarn;
    }

    public void setItemEarn(String itemEarn) {
        this.itemEarn = itemEarn;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Long getItemParentId() {
        return itemParentId;
    }

    public void setItemParentId(Long itemParentId) {
        this.itemParentId = itemParentId;
    }

}