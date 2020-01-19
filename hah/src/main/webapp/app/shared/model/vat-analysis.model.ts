import { IBasket } from 'app/shared/model/basket/basket.model';

export interface IVatAnalysis {
  id?: number;
  vatCode?: string;
  vatElement?: number;
  basket?: IBasket;
}

export const defaultValue: Readonly<IVatAnalysis> = {};
