import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IVatAnalysis } from 'app/shared/model/vat-analysis.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './vat-analysis.reducer';

export interface IVatAnalysisDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VatAnalysisDeleteDialog = (props: IVatAnalysisDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/vat-analysis');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.vatAnalysisEntity.id);
  };

  const { vatAnalysisEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="hahApp.vatAnalysis.delete.question">
        <Translate contentKey="hahApp.vatAnalysis.delete.question" interpolate={{ id: vatAnalysisEntity.id }}>
          Are you sure you want to delete this VatAnalysis?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-vatAnalysis" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ vatAnalysis }: IRootState) => ({
  vatAnalysisEntity: vatAnalysis.entity,
  updateSuccess: vatAnalysis.updateSuccess
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VatAnalysisDeleteDialog);
