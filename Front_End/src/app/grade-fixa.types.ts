export interface Schedule {
  scheduleId: number;
  teacher: { teacherId: number; teacherName: string };
  subject: { subjectId: number; subjectName: string }[];
  time: { timeId: number; startTime: string; endTime: string };
  room: { roomId: number; roomName: string; roomNumber: string; roomFloor: string };
  course: { courseId: number; courseName: string };
  weekDay: { weekDayId: number; weekDay: string };
  createAt?: string;
  updateAt?: string;
}

export interface Time {
  start: string; // Exemplo: "08:00"
  end: string;   // Exemplo: "09:00"
}



export interface Teacher {
  teacherId: number;
  teacherName: string;
}

export interface Subject {
  subjectId: number;
  subjectName: string;
}

export interface WeekDay {  // Renomeado para começar com letra maiúscula
  weekDayId: number;
  weekDay: string;
}

export interface Room {
  roomId: number;
  roomName: string;
  roomNumber: string;
  roomFloor: string;
}

export interface Time {
  timeId: number;
  startTime: string;
  endTime: string;
}

export interface Course {
  courseId: number;
  courseName: string;
  courseSemester: string;
  coursePeriod: string;
  courseSubjects: { subjectId: number }[];
}
