export interface PaymentItem {
  name: string;
  quantity: number;
  price: number;
}

export interface PaymentPayOs {
  orderCode: number;
  amount: number;
  description: string;
  buyerName: string;
  buyerEmail: string;
  buyerPhone: string;
  buyerAddress: string;
  items: PaymentItem[];
  cancelUrl: string;
  returnUrl: string;
  expiredAt: number;
  signature: string;
}
