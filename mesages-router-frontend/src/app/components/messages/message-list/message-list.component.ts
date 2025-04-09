import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Message } from '../../../models/message.model';
import { MessageService } from '../../../services/message.service';
import { MessageDetailComponent } from '../message-detail/message-detail.component';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss']
})
export class MessageListComponent implements OnInit {
  displayedColumns: string[] = ['id','receivedTimestamp', 'content'];
  dataSource = new MatTableDataSource<Message>([]);
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private messageService: MessageService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(event?: PageEvent): void {
    if (event) {
      this.pageIndex = event.pageIndex;
      this.pageSize = event.pageSize;
    }

    this.messageService.getMessages(this.pageIndex, this.pageSize)
      .subscribe(page => {
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
      });

    return event ? undefined : undefined;
  }

  openMessageDetails(message: Message): void {
    this.dialog.open(MessageDetailComponent, {
      width: '600px',
      data: message
    });
  }
}
