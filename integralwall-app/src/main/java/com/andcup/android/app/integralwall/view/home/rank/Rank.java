package com.andcup.android.app.integralwall.view.home.rank;

import java.io.Serializable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/23.
 */
public interface Rank extends Serializable{

    String getType();

    Rank DAY3 = new Rank() {
        @Override
        public String getType() {
            return "3day";
        }
    };

    Rank DAY7 = new Rank() {
        @Override
        public String getType() {
            return "7day";
        }
    };

    Rank WEEK = new Rank() {
        @Override
        public String getType() {
            return "week";
        }
    };

    Rank MONTH = new Rank() {
        @Override
        public String getType() {
            return "month";
        }
    };
}
