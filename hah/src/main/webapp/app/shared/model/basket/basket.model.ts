import { IBasketEntry } from 'app/shared/model/basket/basket-entry.model';
import { IVatAnalysis } from 'app/shared/model/vat-analysis.model';

export interface IBasket {
  id?: number;
  basketId?: string;
  totalPrice?: number;
  txns?: string;
  vatAnalysis?: string;
  basketEntries?: IBasketEntry[];
  vatAnalyses?: IVatAnalysis[];
}

export const defaultValue: Readonly<IBasket> = {};
