import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dayFilter'
})
export class DayFilterPipe implements PipeTransform {
  transform(schedules: any[], day: string): any[] {
    if (!schedules || !day) return [];
    
    return schedules.filter(schedule => 
      schedule.weekday.toLowerCase() === day.toLowerCase()
    );
  }
}
