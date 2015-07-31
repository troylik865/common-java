package org.troy.platform.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;


@Entity
@Table(name = "platform_member")
public class PlatformMember extends IdEntity {
    private static final long serialVersionUID = -4899506899281254849L;


    @Column(length = 45)
    private String            name;

    @Column(length = 20)
    private String            telephone;

    @Column(length = 20)
    private String            idCard;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

}