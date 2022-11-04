package com.yhlo.oa.fx;

import java.util.ResourceBundle;

public enum FxmlView {
    MAIN {
        @Override
		public String title() {
            return getStringFromResourceBundle("app.title");
        }

        @Override
		public String fxml() {
            return "/views/Main.fxml";
        }

    }, 
    VIEW_FRAMEWORK {
        @Override
		public String title() {
            return getStringFromResourceBundle("module.frameWork.title");
        }

        @Override
        public String fxml() {
            return "/views/FrameWork.fxml";
        }

    },
    VIEW_LOGIN {
        @Override
        public String title() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String fxml() {
            return "/views/Login.fxml";
        }

    },
    VIEW_ROLES {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.roles.title");
        }

        @Override
        public String fxml() {
            return "/views/Roles.fxml";
        }

    },
    VIEW_GENERALORDER {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.GeneralOrder.title");
        }

        @Override
        public String fxml() {
            return "/views/GeneralOrder.fxml";
        }

    },
    VIEW_GENERALORDERAPPROVAL {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.generalOrderApproval.title");
        }

        @Override
        public String fxml() {
            return "/views/GeneralOrderApproval.fxml";
        }

    },
    VIEW_CONFIG {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.VIEW_CONFIG_SYN.title");
        }

        @Override
        public String fxml() {
            return "/views/SapConfigTable.fxml";
        }

    },
    VIEW_DATASYN {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.DataSyn.title");
        }

        @Override
        public String fxml() {
            return "/views/DataSyn.fxml";
        }

    },VIEW_WEBVIEW {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.Webview.title");
        }

        @Override
        public String fxml() {
            return "/views/Webview.fxml";
        }

    },VIEW_MATERIAMASTER {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.materiaMaster.title");
        }

        @Override
        public String fxml() {
            return "/views/MateriaMasterData.fxml";
        }

    },VIEW_CUSTOMERDATA {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.customerData.title");
        }

        @Override
        public String fxml() {
            return "/views/CustomerMasterData.fxml";
        }

    },VIEW_REBATEPOLICY {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.rebatePolicy.title");
        }

        @Override
        public String fxml() {
            return "/views/RebatePolicy.fxml";
        }

    },VIEW_REBATEPOLICYSEARCH {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.rebatePolicySearch.title");
        }

        @Override
        public String fxml() {
            return "/views/RebatePolicySearch.fxml";
        }

    },VIEW_REBATEPOLICYAPPROVAL {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.RebatePolicyApproval.title");
        }

        @Override
        public String fxml() {
            return "/views/RebatePolicyApproval.fxml";
        }

    };


	
    
    public abstract String title();
    public abstract String fxml();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
