package com.example.studycrud.dto;

    public class MainDTO {

        private int menuCode;
        private String menuName;
        private String menuText;

        public MainDTO() {
        }

        public MainDTO(int menuCode, String menuName, String menuText) {
            this.menuCode = menuCode;
            this.menuName = menuName;
            this.menuText = menuText;
        }

        @Override
        public String toString() {
            return "MainDTO{" +
                    "menuCode=" + menuCode +
                    ", menuName='" + menuName + '\'' +
                    ", menuText='" + menuText + '\'' +
                    '}';
        }

        public int getMenuCode() {
            return menuCode;
        }

        public void setMenuCode(int menuCode) {
            this.menuCode = menuCode;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuText() {
            return menuText;
        }

        public void setMenuText(String menuText) {
            this.menuText = menuText;
        }
    }
