import { ISubscriptionGroup } from 'app/shared/model/subscription-group.model';

export interface IBranch {
  id?: number;
  branchAddress?: string;
  fad?: string;
  name?: string;
  subscriptionGroup?: ISubscriptionGroup;
}

export const defaultValue: Readonly<IBranch> = {};
