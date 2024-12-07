import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeFilter',
})
export class TimeFilterPipe implements PipeTransform {
  transform(schedules: any[], time: { start: string; end: string }): any[] {
    if (!schedules || !time) return [];
  
    return schedules.filter((schedule) => {
      const [startTime, endTime] = schedule.time.split(' - ');
      return startTime === time.start && endTime === time.end;
    });
  }
}
