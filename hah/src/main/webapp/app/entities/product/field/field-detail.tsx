import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './field.reducer';
import { IField } from 'app/shared/model/product/field.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFieldDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FieldDetail = (props: IFieldDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { fieldEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.productField.detail.title">Field</Translate> [<b>{fieldEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="defaultz">
              <Translate contentKey="hahApp.productField.defaultz">Defaultz</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.defaultz}</dd>
          <dt>
            <span id="editable">
              <Translate contentKey="hahApp.productField.editable">Editable</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.editable ? 'true' : 'false'}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="hahApp.productField.label">Label</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.label}</dd>
          <dt>
            <span id="mandatory">
              <Translate contentKey="hahApp.productField.mandatory">Mandatory</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.mandatory ? 'true' : 'false'}</dd>
          <dt>
            <span id="maxs">
              <Translate contentKey="hahApp.productField.maxs">Maxs</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.maxs}</dd>
          <dt>
            <span id="mins">
              <Translate contentKey="hahApp.productField.mins">Mins</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.mins}</dd>
          <dt>
            <span id="multiple">
              <Translate contentKey="hahApp.productField.multiple">Multiple</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.multiple}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hahApp.productField.name">Name</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.name}</dd>
          <dt>
            <span id="patternz">
              <Translate contentKey="hahApp.productField.patternz">Patternz</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.patternz}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hahApp.productField.type">Type</Translate>
            </span>
          </dt>
          <dd>{fieldEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/field" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field/${fieldEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ field }: IRootState) => ({
  fieldEntity: field.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FieldDetail);
