import { IProduct } from 'app/shared/model/product/product.model';

export interface IRulez {
  id?: number;
  name?: string;
  description?: string;
  rulez?: IProduct;
}

export const defaultValue: Readonly<IRulez> = {};
