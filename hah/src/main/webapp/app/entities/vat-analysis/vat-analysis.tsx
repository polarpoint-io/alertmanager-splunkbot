import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vat-analysis.reducer';
import { IVatAnalysis } from 'app/shared/model/vat-analysis.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVatAnalysisProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const VatAnalysis = (props: IVatAnalysisProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { vatAnalysisList, match } = props;
  return (
    <div>
      <h2 id="vat-analysis-heading">
        <Translate contentKey="hahApp.vatAnalysis.home.title">Vat Analyses</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.vatAnalysis.home.createLabel">Create new Vat Analysis</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {vatAnalysisList && vatAnalysisList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.vatAnalysis.vatCode">Vat Code</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.vatAnalysis.vatElement">Vat Element</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.vatAnalysis.basket">Basket</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vatAnalysisList.map((vatAnalysis, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vatAnalysis.id}`} color="link" size="sm">
                      {vatAnalysis.id}
                    </Button>
                  </td>
                  <td>{vatAnalysis.vatCode}</td>
                  <td>{vatAnalysis.vatElement}</td>
                  <td>{vatAnalysis.basket ? <Link to={`basket/${vatAnalysis.basket.id}`}>{vatAnalysis.basket.basketId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vatAnalysis.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vatAnalysis.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vatAnalysis.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="hahApp.vatAnalysis.home.notFound">No Vat Analyses found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ vatAnalysis }: IRootState) => ({
  vatAnalysisList: vatAnalysis.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatAnalysis);
