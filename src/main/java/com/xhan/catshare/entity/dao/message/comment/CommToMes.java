package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("M")
@EqualsAndHashCode(callSuper = true)
public class CommToMes extends MetaComment {

}
