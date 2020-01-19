import { ICategory } from 'app/shared/model/product/category.model';
import { IBranch } from 'app/shared/model/branch.model';

export interface ISubscriptionGroup {
  id?: number;
  name?: string;
  subscriptionGroups?: ICategory[];
  subscriptionGroups?: IBranch[];
}

export const defaultValue: Readonly<ISubscriptionGroup> = {};
