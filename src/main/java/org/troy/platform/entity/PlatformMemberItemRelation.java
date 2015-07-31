package org.troy.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

@Entity
@Table(name = "platform_member_item_relation")
public class PlatformMemberItemRelation extends IdEntity {
    private static final long serialVersionUID = -7308265476070325515L;

    @Column(length = 11)
    private Long              memberId;

    @Column(length = 11)
    private Long              itemId;

    @Column(length = 11)
    private Integer           itemNum;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

}