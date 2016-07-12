package com.andcup.android.app.integralwall.view.family;

import android.net.Uri;
import android.os.Bundle;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.integralwall.commons.widget.SmartImageView;
import com.yl.android.cpa.R;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/30.
 */
public class MineCardFragment extends GateDialogFragment{

    @Bind(R.id.riv_card)
    SmartImageView mIvCard;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_card;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mIvCard.setImageURI(Uri.parse(UserProvider.getInstance().getUserInfo().getBussinessCard()));
    }
}
