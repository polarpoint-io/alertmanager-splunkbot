import { IProduct } from 'app/shared/model/product/product.model';
import { IField } from 'app/shared/model/product/field.model';

export interface IPage {
  id?: number;
  number?: number;
  predicates?: string;
  protectionLevel?: string;
  quote?: string;
  title?: string;
  page?: IProduct;
  order?: IField;
}

export const defaultValue: Readonly<IPage> = {};
