import { IToken } from 'app/shared/model/token.model';
import { IVatRate } from 'app/shared/model/vat-rate.model';
import { IPage } from 'app/shared/model/product/page.model';
import { IRulez } from 'app/shared/model/rulez.model';
import { ICategory } from 'app/shared/model/product/category.model';

export interface IProduct {
  id?: number;
  additionalReceipts?: string;
  client?: number;
  name?: string;
  description?: string;
  paymentType?: string;
  type?: string;
  vatCode?: string;
  tokens?: IToken[];
  vatRates?: IVatRate[];
  pages?: IPage[];
  rulezs?: IRulez[];
  category?: ICategory;
}

export const defaultValue: Readonly<IProduct> = {};
