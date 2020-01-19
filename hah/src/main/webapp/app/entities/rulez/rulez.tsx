import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './rulez.reducer';
import { IRulez } from 'app/shared/model/rulez.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRulezProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Rulez = (props: IRulezProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { rulezList, match } = props;
  return (
    <div>
      <h2 id="rulez-heading">
        <Translate contentKey="hahApp.rulez.home.title">Rulezs</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="hahApp.rulez.home.createLabel">Create new Rulez</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {rulezList && rulezList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.rulez.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.rulez.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="hahApp.rulez.rulez">Rulez</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rulezList.map((rulez, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${rulez.id}`} color="link" size="sm">
                      {rulez.id}
                    </Button>
                  </td>
                  <td>{rulez.name}</td>
                  <td>{rulez.description}</td>
                  <td>{rulez.rulez ? <Link to={`product/${rulez.rulez.id}`}>{rulez.rulez.code}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${rulez.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rulez.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rulez.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="hahApp.rulez.home.notFound">No Rulezs found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ rulez }: IRootState) => ({
  rulezList: rulez.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Rulez);
