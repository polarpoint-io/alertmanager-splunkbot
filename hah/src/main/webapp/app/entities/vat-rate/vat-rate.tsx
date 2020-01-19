import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vat-rate.reducer';
import { IVatRate } from 'app/shared/model/vat-rate.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVatRateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const VatRate = (props: IVatRateProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { vatRateList, match } = props;
  return (
    <div>
      <h2 id="vat-rate-heading">
        <Translate contentKey="hahApp.vatRate.home.title">Vat Rates</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.vatRate.home.createLabel">Create new Vat Rate</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {vatRateList && vatRateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.vatRate.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.vatRate.rate">Rate</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.vatRate.vatRate">Vat Rate</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vatRateList.map((vatRate, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vatRate.id}`} color="link" size="sm">
                      {vatRate.id}
                    </Button>
                  </td>
                  <td>{vatRate.code}</td>
                  <td>{vatRate.rate}</td>
                  <td>{vatRate.vatRate ? <Link to={`product/${vatRate.vatRate.id}`}>{vatRate.vatRate.code}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vatRate.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vatRate.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vatRate.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="hahApp.vatRate.home.notFound">No Vat Rates found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ vatRate }: IRootState) => ({
  vatRateList: vatRate.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatRate);
