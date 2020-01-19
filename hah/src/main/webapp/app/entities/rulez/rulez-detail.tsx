import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rulez.reducer';
import { IRulez } from 'app/shared/model/rulez.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRulezDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RulezDetail = (props: IRulezDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { rulezEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="hahApp.rulez.detail.title">Rulez</Translate> [<b>{rulezEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="hahApp.rulez.name">Name</Translate>
            </span>
          </dt>
          <dd>{rulezEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hahApp.rulez.description">Description</Translate>
            </span>
          </dt>
          <dd>{rulezEntity.description}</dd>
          <dt>
            <Translate contentKey="hahApp.rulez.rulez">Rulez</Translate>
          </dt>
          <dd>{rulezEntity.rulez ? rulezEntity.rulez.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/rulez" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rulez/${rulezEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ rulez }: IRootState) => ({
  rulezEntity: rulez.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RulezDetail);
