import { IBasket } from 'app/shared/model/basket/basket.model';

export interface IBasketEntry {
  id?: number;
  basketId?: string;
  canEdit?: boolean;
  prodictId?: number;
  quantity?: number;
  refundable?: boolean;
  removeable?: boolean;
  totalPrice?: number;
  transactionId?: string;
  unitPrice?: number;
  vatCode?: string;
  vatElement?: string;
  basket?: IBasket;
}

export const defaultValue: Readonly<IBasketEntry> = {
  canEdit: false,
  refundable: false,
  removeable: false
};
