package Hotel;

public class Discount {

    public enum DISCOUNT_CODES {

        I_WORK_HERE     (0, "I_WORK_HERE"),
        STAY4_GET1      (1, "STAY4_GET1"),
        PAYDAY          (2, "PAYDAY");

        private final int codeID;
        private final String stringID;

        private DISCOUNT_CODES(int codeID, String stringID) {
            this.codeID = codeID;
            this.stringID = stringID;
        }

        /**
         * Returns the discount code's ID.
         */
        public int getCodeID() {
            return codeID;
        }


        /**
         * Returns the discount code's string ID.
         */
        public String getStringID() {
            return stringID;
        }
    }
}