package Hotel;

public class Discount {


    public enum DISCOUNT_CODES {

        I_WORK_HERE {
            @Override // TODO: Implement this
            public Result applyDiscountCode(Reservation reservation) {
                return null;
            }
        },
        STAY4_GET1 {
            @Override // TODO: Implement this
            public Result applyDiscountCode(Reservation reservation) {
                return null;
            }
        },
        PAYDAY {
            @Override // TODO: Implement this
            public Result applyDiscountCode(Reservation reservation) {
                return null;
            }
        };

        public abstract Result applyDiscountCode(Reservation reservation);
    }
}