import { IPage } from 'app/shared/model/product/page.model';

export interface IField {
  id?: number;
  defaultz?: string;
  editable?: boolean;
  label?: string;
  mandatory?: boolean;
  maxs?: string;
  mins?: string;
  multiple?: number;
  name?: string;
  patternz?: string;
  type?: string;
  pages?: IPage[];
}

export const defaultValue: Readonly<IField> = {
  editable: false,
  mandatory: false
};
