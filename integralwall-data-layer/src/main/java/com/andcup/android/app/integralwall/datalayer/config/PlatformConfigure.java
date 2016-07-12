package com.andcup.android.app.integralwall.datalayer.config;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/31.
 */
public interface PlatformConfigure {

    String getPlatformUrl();

    boolean isDebug();

    boolean isCheck();

    boolean isMonkey();

    PlatformConfigure DEVELOPER = new PlatformConfigure() {
        @Override
        public String getPlatformUrl() {
            return "http://site.lyzip.com";
        }

        @Override
        public boolean isDebug() {
            return true;
        }

        @Override
        public boolean isCheck() {
            return false;
        }

        @Override
        public boolean isMonkey() {
            return false;
        }
    };

    PlatformConfigure TEST = new PlatformConfigure() {
        @Override
        public String getPlatformUrl() {
            return "http://lyztest.lyzip.com";
        }

        @Override
        public boolean isDebug() {
            return false;
        }

        @Override
        public boolean isCheck() {
            return true;
        }

        @Override
        public boolean isMonkey() {
            return false;
        }
    };

    PlatformConfigure RELEASE = new PlatformConfigure() {
        @Override
        public String getPlatformUrl() {
            return "http://www.lyzip.com";
        }

        @Override
        public boolean isDebug() {
            return false;
        }

        @Override
        public boolean isCheck() {
            return true;
        }

        @Override
        public boolean isMonkey() {
            return false;
        }
    };
}
