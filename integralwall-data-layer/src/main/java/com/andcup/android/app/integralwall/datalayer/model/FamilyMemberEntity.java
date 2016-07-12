package com.andcup.android.app.integralwall.datalayer.model;

import com.andcup.android.app.integralwall.datalayer.model.db.DbColumn;
import com.andcup.android.database.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amos on 2015/9/8.
 */
public class FamilyMemberEntity extends IntegralWallEntity implements Serializable {

    @Column(name = DbColumn.USER_UID)
    String mUid;

    @JsonProperty("total_member")
    @Column
    int mTotalMember;

    @JsonProperty("each_level_member_total_score")
    @Column
    int mEachLevelMemberTotalScore;


    @JsonProperty("each_level_member_total")
    @Column
    int mEachLevelMemberTotal;

    @JsonProperty("total")
    @Column
    int mTotal;

    @JsonProperty("list")
    @Column(name = DbColumn.FAMILY_MEMBER)
    List<MemberEntity> mMembers;

    public List<MemberEntity> getMembers() {
        return mMembers;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public int getTotalMember() {
        return mTotalMember;
    }

    public int getLevelTotal() {
        return mEachLevelMemberTotal;
    }
}
