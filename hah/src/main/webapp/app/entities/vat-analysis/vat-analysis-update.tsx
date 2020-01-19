import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBasket } from 'app/shared/model/basket/basket.model';
import { getEntities as getBaskets } from 'app/entities/basket/basket/basket.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vat-analysis.reducer';
import { IVatAnalysis } from 'app/shared/model/vat-analysis.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVatAnalysisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VatAnalysisUpdate = (props: IVatAnalysisUpdateProps) => {
  const [basketId, setBasketId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { vatAnalysisEntity, baskets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/vat-analysis');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBaskets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...vatAnalysisEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hahApp.vatAnalysis.home.createOrEditLabel">
            <Translate contentKey="hahApp.vatAnalysis.home.createOrEditLabel">Create or edit a VatAnalysis</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : vatAnalysisEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="vat-analysis-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="vat-analysis-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="vatCodeLabel" for="vat-analysis-vatCode">
                  <Translate contentKey="hahApp.vatAnalysis.vatCode">Vat Code</Translate>
                </Label>
                <AvField id="vat-analysis-vatCode" type="text" name="vatCode" />
              </AvGroup>
              <AvGroup>
                <Label id="vatElementLabel" for="vat-analysis-vatElement">
                  <Translate contentKey="hahApp.vatAnalysis.vatElement">Vat Element</Translate>
                </Label>
                <AvField id="vat-analysis-vatElement" type="text" name="vatElement" />
              </AvGroup>
              <AvGroup>
                <Label for="vat-analysis-basket">
                  <Translate contentKey="hahApp.vatAnalysis.basket">Basket</Translate>
                </Label>
                <AvInput id="vat-analysis-basket" type="select" className="form-control" name="basket.id">
                  <option value="" key="0" />
                  {baskets
                    ? baskets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.basketId}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/vat-analysis" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  baskets: storeState.basket.entities,
  vatAnalysisEntity: storeState.vatAnalysis.entity,
  loading: storeState.vatAnalysis.loading,
  updating: storeState.vatAnalysis.updating,
  updateSuccess: storeState.vatAnalysis.updateSuccess
});

const mapDispatchToProps = {
  getBaskets,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatAnalysisUpdate);
