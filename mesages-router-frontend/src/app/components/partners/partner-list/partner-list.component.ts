import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Partner } from '../../../models/partner.model';
import { PartnerService } from '../../../services/partner.service';
import { PartnerAddComponent } from '../partner-add/partner-add.component';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-partner-list',
  templateUrl: './partner-list.component.html',
  styleUrls: ['./partner-list.component.scss']
})
export class PartnerListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'actions'];
  dataSource = new MatTableDataSource<Partner>([]);
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private partnerService: PartnerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadPartners();
  }

  loadPartners(event?: PageEvent): void {
    if (event) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
    }

    this.partnerService.getPartners(this.pageIndex, this.pageSize)
      .subscribe(page => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      });

    return event ? undefined : undefined;
  }

  openAddPartnerDialog(): void {
    const dialogRef = this.dialog.open(PartnerAddComponent, {
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadPartners();
        this.snackBar.open('Partenaire ajouté avec succès', 'Fermer', {
          duration: 3000
        });
      }
    });
  }

  deletePartner(partner: Partner): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmer la suppression',
        message: `Êtes-vous sûr de vouloir supprimer le partenaire "${partner.alias}" ?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.partnerService.deletePartner(partner.id).subscribe({
          next: () => {
            this.loadPartners();
            this.snackBar.open('Partenaire supprimé avec succès', 'Fermer', {
              duration: 3000
            });
          },
          error: (error) => {
            this.snackBar.open('Erreur lors de la suppression du partenaire', 'Fermer', {
              duration: 5000
            });
            console.error('Error deleting partner:', error);
          }
        });
      }
    });
  }
}
