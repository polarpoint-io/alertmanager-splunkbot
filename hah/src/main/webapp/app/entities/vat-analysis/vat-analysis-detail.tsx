import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vat-analysis.reducer';
import { IVatAnalysis } from 'app/shared/model/vat-analysis.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVatAnalysisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VatAnalysisDetail = (props: IVatAnalysisDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { vatAnalysisEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.vatAnalysis.detail.title">VatAnalysis</Translate> [<b>{vatAnalysisEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="vatCode">
              <Translate contentKey="hahApp.vatAnalysis.vatCode">Vat Code</Translate>
            </span>
          </dt>
          <dd>{vatAnalysisEntity.vatCode}</dd>
          <dt>
            <span id="vatElement">
              <Translate contentKey="hahApp.vatAnalysis.vatElement">Vat Element</Translate>
            </span>
          </dt>
          <dd>{vatAnalysisEntity.vatElement}</dd>
          <dt>
            <Translate contentKey="hahApp.vatAnalysis.basket">Basket</Translate>
          </dt>
          <dd>{vatAnalysisEntity.basket ? vatAnalysisEntity.basket.basketId : ''}</dd>
        </dl>
        <Button tag={Link} to="/vat-analysis" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vat-analysis/${vatAnalysisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ vatAnalysis }: IRootState) => ({
  vatAnalysisEntity: vatAnalysis.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatAnalysisDetail);
