import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { PartnerService } from '../../../services/partner.service';

@Component({
  selector: 'app-partner-add',
  templateUrl: './partner-add.component.html',
  styleUrls: ['./partner-add.component.scss']
})
export class PartnerAddComponent {
  partnerForm: FormGroup;
  directions = ['INBOUND', 'OUTBOUND'];
  processedFlowTypes = ['MESSAGE', 'ALERTING', 'NOTIFICATION'];
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private partnerService: PartnerService,
    public dialogRef: MatDialogRef<PartnerAddComponent>
  ) {
    this.partnerForm = this.fb.group({
      alias: ['', [Validators.required]],
      type: ['', [Validators.required]],
      direction: ['INBOUND', [Validators.required]],
      application: [''],
      processedFlowType: ['MESSAGE', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.partnerForm.valid) {
      this.isSubmitting = true;
      this.partnerService.addPartner(this.partnerForm.value).subscribe({
        next: (partner) => {
          this.dialogRef.close(partner);
        },
        error: (error) => {
          console.error('Error adding partner:', error);
          this.isSubmitting = false;
        }
      });
    } else {
      // Mark all fields as touched to trigger validation messages
      Object.keys(this.partnerForm.controls).forEach(key => {
        const control = this.partnerForm.get(key);
        control?.markAsTouched();
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
