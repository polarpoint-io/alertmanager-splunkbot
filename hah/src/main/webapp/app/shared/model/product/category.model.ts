import { IProduct } from 'app/shared/model/product/product.model';
import { ISubscriptionGroup } from 'app/shared/model/subscription-group.model';

export interface ICategory {
  id?: number;
  deviceRestrictions?: string;
  products?: IProduct[];
  subscriptionGroup?: ISubscriptionGroup;
}

export const defaultValue: Readonly<ICategory> = {};
