import { IProduct } from 'app/shared/model/product/product.model';

export interface IVatRate {
  id?: number;
  code?: string;
  rate?: number;
  vatRate?: IProduct;
}

export const defaultValue: Readonly<IVatRate> = {};
