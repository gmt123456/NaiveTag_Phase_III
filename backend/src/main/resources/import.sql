-- insert administrator, requester with some money, worker
insert into AdminPO values ('admin','SUPREME','3fed7a346e430ea4c2aa10250928f4de');
insert into RequesterPO values ('frogR@mail.com','defaultImage/covers/2.png',10000,'2018-06-09 14:05:01','frog','ae217fc8b657e233f3207e05afb0ba21');

-- Insert TaskCommitment
INSERT INTO naivetag.TaskCommitmentLogPO (commitTime, subTaskId, taskId, userEmail) VALUES ('2018-07-09 13:30:20', 63, 12, 'frog@worker.com');
INSERT INTO naivetag.TaskCommitmentLogPO (commitTime, subTaskId, taskId, userEmail) VALUES ('2018-06-09 13:30:20', 63, 12, 'frog@worker.com');
INSERT INTO naivetag.TaskCommitmentLogPO (commitTime, subTaskId, taskId, userEmail) VALUES ('2018-05-09 13:30:20', 63, 12, 'frog@worker.com');
INSERT INTO naivetag.TaskCommitmentLogPO (commitTime, subTaskId, taskId, userEmail) VALUES ('2018-06-09 13:30:20', 63, 12, 'frog@worker.com');
INSERT INTO naivetag.TaskCommitmentLogPO (commitTime, subTaskId, taskId, userEmail) VALUES ('2018-04-09 13:30:20', 63, 12, 'frog@worker.com');
