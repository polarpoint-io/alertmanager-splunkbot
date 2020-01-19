import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vat-rate.reducer';
import { IVatRate } from 'app/shared/model/vat-rate.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVatRateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VatRateDetail = (props: IVatRateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { vatRateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.vatRate.detail.title">VatRate</Translate> [<b>{vatRateEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="code">
              <Translate contentKey="hahApp.vatRate.code">Code</Translate>
            </span>
          </dt>
          <dd>{vatRateEntity.code}</dd>
          <dt>
            <span id="rate">
              <Translate contentKey="hahApp.vatRate.rate">Rate</Translate>
            </span>
          </dt>
          <dd>{vatRateEntity.rate}</dd>
          <dt>
            <Translate contentKey="hahApp.vatRate.vatRate">Vat Rate</Translate>
          </dt>
          <dd>{vatRateEntity.vatRate ? vatRateEntity.vatRate.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/vat-rate" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vat-rate/${vatRateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ vatRate }: IRootState) => ({
  vatRateEntity: vatRate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatRateDetail);
