import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISubscriptionGroup } from 'app/shared/model/subscription-group.model';
import { getEntities as getSubscriptionGroups } from 'app/entities/subscription-group/subscription-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './category.reducer';
import { ICategory } from 'app/shared/model/product/category.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CategoryUpdate = (props: ICategoryUpdateProps) => {
  const [subscriptionGroupId, setSubscriptionGroupId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { categoryEntity, subscriptionGroups, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/category');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSubscriptionGroups();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...categoryEntity,
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
          <h2 id="hahApp.productCategory.home.createOrEditLabel">
            <Translate contentKey="hahApp.productCategory.home.createOrEditLabel">Create or edit a Category</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : categoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="category-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="category-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="deviceRestrictionsLabel" for="category-deviceRestrictions">
                  <Translate contentKey="hahApp.productCategory.deviceRestrictions">Device Restrictions</Translate>
                </Label>
                <AvField
                  id="category-deviceRestrictions"
                  type="text"
                  name="deviceRestrictions"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="category-subscriptionGroup">
                  <Translate contentKey="hahApp.productCategory.subscriptionGroup">Subscription Group</Translate>
                </Label>
                <AvInput id="category-subscriptionGroup" type="select" className="form-control" name="subscriptionGroup.id">
                  <option value="" key="0" />
                  {subscriptionGroups
                    ? subscriptionGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/category" replace color="info">
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
  subscriptionGroups: storeState.subscriptionGroup.entities,
  categoryEntity: storeState.category.entity,
  loading: storeState.category.loading,
  updating: storeState.category.updating,
  updateSuccess: storeState.category.updateSuccess
});

const mapDispatchToProps = {
  getSubscriptionGroups,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CategoryUpdate);
