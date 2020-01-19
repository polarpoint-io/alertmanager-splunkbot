import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './field.reducer';
import { IField } from 'app/shared/model/product/field.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFieldUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FieldUpdate = (props: IFieldUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { fieldEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/field' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...fieldEntity,
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
          <h2 id="hahApp.productField.home.createOrEditLabel">
            <Translate contentKey="hahApp.productField.home.createOrEditLabel">Create or edit a Field</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fieldEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="field-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="field-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="defaultzLabel" for="field-defaultz">
                  <Translate contentKey="hahApp.productField.defaultz">Defaultz</Translate>
                </Label>
                <AvField id="field-defaultz" type="text" name="defaultz" />
              </AvGroup>
              <AvGroup check>
                <Label id="editableLabel">
                  <AvInput id="field-editable" type="checkbox" className="form-check-input" name="editable" />
                  <Translate contentKey="hahApp.productField.editable">Editable</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="labelLabel" for="field-label">
                  <Translate contentKey="hahApp.productField.label">Label</Translate>
                </Label>
                <AvField id="field-label" type="text" name="label" />
              </AvGroup>
              <AvGroup check>
                <Label id="mandatoryLabel">
                  <AvInput id="field-mandatory" type="checkbox" className="form-check-input" name="mandatory" />
                  <Translate contentKey="hahApp.productField.mandatory">Mandatory</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="maxsLabel" for="field-maxs">
                  <Translate contentKey="hahApp.productField.maxs">Maxs</Translate>
                </Label>
                <AvField id="field-maxs" type="text" name="maxs" />
              </AvGroup>
              <AvGroup>
                <Label id="minsLabel" for="field-mins">
                  <Translate contentKey="hahApp.productField.mins">Mins</Translate>
                </Label>
                <AvField id="field-mins" type="text" name="mins" />
              </AvGroup>
              <AvGroup>
                <Label id="multipleLabel" for="field-multiple">
                  <Translate contentKey="hahApp.productField.multiple">Multiple</Translate>
                </Label>
                <AvField id="field-multiple" type="string" className="form-control" name="multiple" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="field-name">
                  <Translate contentKey="hahApp.productField.name">Name</Translate>
                </Label>
                <AvField id="field-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="patternzLabel" for="field-patternz">
                  <Translate contentKey="hahApp.productField.patternz">Patternz</Translate>
                </Label>
                <AvField id="field-patternz" type="text" name="patternz" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="field-type">
                  <Translate contentKey="hahApp.productField.type">Type</Translate>
                </Label>
                <AvField id="field-type" type="text" name="type" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/field" replace color="info">
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
  fieldEntity: storeState.field.entity,
  loading: storeState.field.loading,
  updating: storeState.field.updating,
  updateSuccess: storeState.field.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FieldUpdate);
